require_relative '../property_generator'
require_relative '../default_value/long_default_value'


class LongPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value? and default_value_code != '0' and default_value_code != '0L'
        "    private long #{name} = #{default_value_code};\n"
      else
        "    private long #{name};\n"
      end
    else
      "    private @Nullable Long #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public long #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable Long #{field_conf.getter_name}() {\n"
    end
    if virtual?
      code << "#{virtual_code}\n"
    else
      code << "        return #{name};\n"
    end
    code << "    }\n"
  end

  def generate_setter_code
    code = ''
    if required?
      code << "    public void set#{field_conf.camel_case_name}(long #{name}) {\n"
      if store_field?
        code << "        if (#{name} != this.#{name}) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    else
      code << "    public void set#{field_conf.camel_case_name}(@Nullable Long #{name}) {\n"
      if store_field?
        code << "        if (!Objects.equals(this.#{name}, #{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    end
  end

  def generate_increment_code
    code = ''
    if required?
      code << "    public long increase#{field_conf.camel_case_name}() {\n"
      code << "        #{field_changed_code}\n"
      code << "        return ++#{name};\n"
      code << "    }\n"
    else
      code << "    public @Nullable Long increase#{field_conf.camel_case_name}() {\n"
      code << "        if (#{name} != null) {\n"
      code << "            #{field_changed_code}\n"
      code << "            return ++#{name};\n"
      code << "        }\n"
      code << "        return null;\n"
      code << "    }\n"
    end
  end

  private
  def default_value_code
    LongDefaultValue.instance.generate_code(@config, @model_conf, @field_conf)
  end

end
