require_relative '../property_generator'
require_relative '../default_value/date_default_value'


class DatePropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private LocalDate #{name} = #{default_value_code};\n"
      else
        "    private LocalDate #{name} = LocalDate.EPOCH;\n"
      end
    else
      "    private @Nullable LocalDate #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public LocalDate #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable LocalDate #{field_conf.getter_name}() {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(LocalDate #{name}) {\n"
      if store_field?
        code << "        if (!#{name}.equals(this.#{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    else
      code << "    public void set#{field_conf.camel_case_name}(@Nullable LocalDate #{name}) {\n"
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

  private
  def default_value_code
    DateDefaultValue.instance.generate_code(@config, @model_conf, @field_conf)
  end

end
