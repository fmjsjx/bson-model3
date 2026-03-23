require_relative '../default_value'
require 'json'


class StringDefaultValue < DefaultValue

  class << self
    def generate_code(config, model_conf, field_conf)
      field_conf.default.to_json
    end
  end

end
