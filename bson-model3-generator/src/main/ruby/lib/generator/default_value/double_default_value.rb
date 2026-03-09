require_relative '../default_value'


class DoubleDefaultValue < DefaultValue

  @@instance = DoubleDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
    case field_conf.default.upcase
    when 'NAN'
      'Double.NaN'
    when '+INF'
      'Double.POSITIVE_INFINITY'
    when '-INF'
      'Double.NEGATIVE_INFINITY'
    when 'MIN'
      'Double.MIN_VALUE'
    when 'MAX'
      'Double.MAX_VALUE'
    else
      field_conf.default
    end
  end

end
