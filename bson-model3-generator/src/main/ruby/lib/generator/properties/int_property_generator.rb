require_relative '../property_generator'


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

  private
  def default_value_code 
    case field_conf.default.upcase
    when 'MIN'
      'Integer.MIN_VALUE'
    when 'MAX'
      'Integer.MAX_VALUE'
    else
      field_conf.default
    end
  end

end
