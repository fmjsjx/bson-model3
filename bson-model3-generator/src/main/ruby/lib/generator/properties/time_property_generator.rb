require_relative '../property_generator'
require 'json'


class TimePropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private LocalTime #{name} = #{default_value_code};\n"
      else
        "    private LocalTime #{name} = LocalTime.MIDNIGHT;\n"
      end
    else
      "    private @Nullable LocalTime #{name};\n"
    end
  end

  private
  def default_value_code
    value = field_conf.default
    case value.upcase
    when 'MIN'
      'LocalTime.MIN'
    when 'MAX'
      'LocalTime.MAX'
    when 'MIDNIGHT'
      'LocalTime.MIDNIGHT'
    when 'NOON'
      'LocalTime.NOON'
    when 'NOW'
      'LocalTime.now()'
    else
      if model_conf.consts.any? { |const_conf| const_conf.type == 'time' and const_conf.name == value }
        value
      else
        "LocalTime.parse(#{value.to_json})"
      end
    end
  end

end
