require_relative '../default_value'


class DecimalDefaultValue < DefaultValue

  @@instance = DecimalDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
    case field_conf.default.upcase
    when 'ZERO'
      'BigDecimal.ZERO'
    when 'ONE'
      'BigDecimal.ONE'
    when 'TEN'
      'BigDecimal.TEN'
    else
      "new BigDecimal(\"#{field_conf.default}\")"
    end
  end

end
