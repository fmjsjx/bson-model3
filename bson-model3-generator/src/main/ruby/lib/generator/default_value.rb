class DefaultValue

  class << self

    def generate_code(config, model_conf, field_conf)
      case field_conf.type
      when 'int'
        IntDefaultValue.generate_code(config, model_conf, field_conf)
      when 'long'
        LongDefaultValue.generate_code(config, model_conf, field_conf)
      when 'double'
        DoubleDefaultValue.generate_code(config, model_conf, field_conf)
      when 'decimal'
        DecimalDefaultValue.generate_code(config, model_conf, field_conf)
      when 'boolean'
        BooleanDefaultValue.generate_code(config, model_conf, field_conf)
      when 'string'
        StringDefaultValue.generate_code(config, model_conf, field_conf)
      when 'date'
        DateDefaultValue.generate_code(config, model_conf, field_conf)
      when 'time'
        TimeDefaultValue.generate_code(config, model_conf, field_conf)
      when 'datetime'
        DateTimeDefaultValue.generate_code(config, model_conf, field_conf)
      else
        field_conf.default
      end
    end

  end



end

require_relative 'default_value/int_default_value'
require_relative 'default_value/long_default_value'
require_relative 'default_value/double_default_value'
require_relative 'default_value/decimal_default_value'
require_relative 'default_value/boolean_default_value'
require_relative 'default_value/string_default_value'
require_relative 'default_value/date_default_value'
require_relative 'default_value/time_default_value'
require_relative 'default_value/date_time_default_value'
