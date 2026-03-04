require_relative '../property_generator'
require 'json'


class DateTimePropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private LocalDateTime #{name} = #{default_value_code};\n"
      else
        "    private LocalDateTime #{name} = BsonModelConstants.EPOCH_DATE_TIME;\n"
      end
    else
      "    private @Nullable LocalDateTime #{name};\n"
    end
  end

  private
  def default_value_code
    value = field_conf.default
    case value.upcase
    when 'MIN'
      'LocalDateTime.MIN'
    when 'MAX'
      'LocalDateTime.MAX'
    when 'EPOCH'
      'BsonModelConstants.EPOCH_DATE_TIME'
    when 'NOW'
      'LocalDateTime.now()'
    else
      if model_conf.consts.any? { |const_conf| const_conf.type == 'datetime' and const_conf.name == value }
        value
      else
        "LocalDateTime.parse(#{value.to_json})"
      end
    end
  end

end
