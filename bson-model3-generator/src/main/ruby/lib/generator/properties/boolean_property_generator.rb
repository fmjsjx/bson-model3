require_relative '../property_generator'


class BooleanPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value? and default_value_code != 'false'
        "    private boolean #{name} = #{default_value_code};\n"
      else
        "    private boolean #{name};\n"
      end
    else
      "    private @Nullable Boolean #{name};\n"
    end
  end

  private
  def default_value_code
    case field_conf.default.downcase
    when 'true', '1', 'yes', 'y', 'on'
      'true'
    else
      'false'
    end
  end

end
