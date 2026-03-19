require_relative '../default_value'


class DecimalDefaultValue < DefaultValue

  class << self
    def generate_code(config, model_conf, field_conf)
      value = field_conf.default
      case value.upcase
      when 'ZERO'
        'BigDecimal.ZERO'
      when 'ONE'
        'BigDecimal.ONE'
      when 'TEN'
        'BigDecimal.TEN'
      else
        if model_conf.consts.any? { |const_conf| const_conf.type == 'decimal' and const_conf.name == value }
          value
        else
          "new BigDecimal(\"#{value}\")"
        end
      end
    end
  end

end
