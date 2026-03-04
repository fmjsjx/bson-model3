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
