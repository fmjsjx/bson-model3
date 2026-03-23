require_relative '../default_value'
require 'json'


class DateDefaultValue < DefaultValue

  class << self
    def generate_code(config, model_conf, field_conf)
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

end
