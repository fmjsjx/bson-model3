require_relative '../property_generator'


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

  private
  def default_value_code
    case field_conf.default.upcase
    when 'MIN'
      'Long.MIN_VALUE'
    when 'MAX'
      'Long.MAX_VALUE'
    else
      field_conf.default
    end
  end

end
