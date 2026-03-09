require_relative '../default_value'


class LongDefaultValue < DefaultValue

  @@instance = LongDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
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
