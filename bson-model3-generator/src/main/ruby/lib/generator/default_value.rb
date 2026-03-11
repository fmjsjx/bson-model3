class DefaultValue

  @@instance = DefaultValue.new

  class << self

    def from(field_conf)
      case field_conf.type
      when 'int'
        IntDefaultValue.instance
      when 'long'
        LongDefaultValue.instance
      when 'double'
        DoubleDefaultValue.instance
      when 'decimal'
        DecimalDefaultValue.instance
      when 'boolean'
        BooleanDefaultValue.instance
      when 'string'
        StringDefaultValue.instance
      when 'date'
        DateDefaultValue.instance
      when 'time'
        TimeDefaultValue.instance
      when 'datetime'
        DateTimeDefaultValue.instance
      else
        @@instance
      end
    end
  end

  def generate_code(config, model_conf, field_conf)
    field_conf.default
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
