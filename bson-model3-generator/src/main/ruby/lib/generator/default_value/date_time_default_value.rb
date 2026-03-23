require_relative '../default_value'
require 'json'


class DateTimeDefaultValue < DefaultValue

  class << self
    def generate_code(config, model_conf, field_conf)
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

end
