require_relative '../default_value'


class DoubleDefaultValue < DefaultValue

  class << self
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

end
