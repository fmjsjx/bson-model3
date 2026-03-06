require_relative '../property_generator'


class DoublePropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        if default_value_code == '0.0'
          "    private double #{name};\n"
        else
          "    private double #{name} = #{default_value_code};\n"
        end
      else
        "    private double #{name} = Double.NaN;\n"
      end
    else
      "    private @Nullable Double #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public double #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable Double #{field_conf.getter_name}() {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(double #{name}) {\n"
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
      code << "    public void set#{field_conf.camel_case_name}(@Nullable Double #{name}) {\n"
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
    case field_conf.default.upcase
    when 'NAN'
      'Double.NaN'
    when '+INF'
      'Double.POSITIVE_INFINITY'
    when '-INF'
      'Double.NEGATIVE_INFINITY'
    when 'MIN'
      'Double.MIN_VALUE'
    when 'MAX'
      'Double.MAX_VALUE'
    else
      field_conf.default
    end
  end

end
