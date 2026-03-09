require_relative '../property_generator'
require_relative '../default_value/int_default_value'


class IntPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value? and default_value_code != '0'
        "    private int #{name} = #{default_value_code};\n"
      else
        "    private int #{name};\n"
      end
    else
      "    private @Nullable Integer #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public int #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable Integer #{field_conf.getter_name}() {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(int #{name}) {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(@Nullable Integer #{name}) {\n"
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
      code << "    public int increase#{field_conf.camel_case_name}() {\n"
      code << "        #{field_changed_code}\n"
      code << "        return ++#{name};\n"
      code << "    }\n"
    else
      code << "    public @Nullable Integer increase#{field_conf.camel_case_name}() {\n"
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
    IntDefaultValue.generate_class_code(@config, @model_conf, @field_conf)
  end

end
