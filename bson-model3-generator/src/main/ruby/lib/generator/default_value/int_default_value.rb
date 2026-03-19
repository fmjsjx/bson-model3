require_relative '../default_value'


class IntDefaultValue < DefaultValue

  class << self
    def generate_code(config, model_conf, field_conf)
      case field_conf.default.upcase
      when 'MIN'
        'Integer.MIN_VALUE'
      when 'MAX'
        'Integer.MAX_VALUE'
      else
        field_conf.default
      end
    end
  end

end
