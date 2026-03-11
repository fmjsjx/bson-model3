require_relative '../property_generator'
require_relative '../default_value/decimal_default_value'


class DecimalPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private BigDecimal #{name} = #{default_value_code};\n"
      else
        "    private BigDecimal #{name} = BigDecimal.ZERO;\n"
      end
    else
      "    private @Nullable BigDecimal #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public BigDecimal #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable BigDecimal #{field_conf.getter_name}() {\n"
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
      code << "    public void #{field_conf.setter_name}(BigDecimal #{name}) {\n"
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
      code << "    public void #{field_conf.setter_name}(@Nullable BigDecimal #{name}) {\n"
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
    DecimalDefaultValue.instance.generate_code(@config, @model_conf, @field_conf)
  end

end
