require_relative '../default_value'
require 'json'


class TimeDefaultValue < DefaultValue

  @@instance = TimeDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
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
