require_relative '../default_value'


class BooleanDefaultValue < DefaultValue

  @@instance = BooleanDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
    case field_conf.default.downcase
    when 'true', '1', 'yes', 'y', 'on'
      'true'
    else
      'false'
    end
  end

end
