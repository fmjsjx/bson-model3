require_relative '../default_value'
require 'json'


class StringDefaultValue < DefaultValue

  @@instance = StringDefaultValue.new

  class << self
    def instance
      @@instance
    end
  end

  def generate_code(config, model_conf, field_conf)
    field_conf.default.to_json
  end

end
