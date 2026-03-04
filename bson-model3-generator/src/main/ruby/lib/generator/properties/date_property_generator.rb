require_relative '../property_generator'
require 'json'


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

  private
  def default_value_code
    value = field_conf.default
    case value.upcase
    when 'MIN'
      'LocalDate.MIN'
    when 'MAX'
      'LocalDate.MAX'
    when 'EPOCH'
      'LocalDate.EPOCH'
    when 'NOW'
      'LocalDate.now()'
    else
      if model_conf.consts.any? { |const_conf| const_conf.type == 'date' and const_conf.name == value }
        value
      else
        "LocalDate.parse(#{value.to_json})"
      end
    end
  end

end
