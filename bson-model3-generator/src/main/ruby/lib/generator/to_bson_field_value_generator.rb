class ToBsonFieldValueGenerator

  class << self

    def from(field_conf, config, model_conf)
      case field_conf.type
      when 'int'
        ToBsonIntValueGenerator.new(config, model_conf, field_conf)
      when 'long'
        ToBsonLongValueGenerator.new(config, model_conf, field_conf)
      when 'double'
        ToBsonDoubleValueGenerator.new(config, model_conf, field_conf)
      when 'decimal'
        ToBsonDecimalValueGenerator.new(config, model_conf, field_conf)
      when 'boolean'
        ToBsonBooleanValueGenerator.new(config, model_conf, field_conf)
      when 'string'
        ToBsonStringValueGenerator.new(config, model_conf, field_conf)
      when 'date'
        ToBsonDateValueGenerator.new(config, model_conf, field_conf)
      when 'time'
        ToBsonTimeValueGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        ToBsonDateTimeValueGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        ToBsonObjectIdValueGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        ToBsonUuidValueGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        ToBsonStdListValueGenerator.new(config, model_conf, field_conf)
      else
        new(config, model_conf, field_conf)
      end
    end

  end

  attr_reader :config, :model_conf, :field_conf, :temp_field_name

  def initialize(config, model_conf, field_conf)
    @config = config
    @model_conf = model_conf
    @field_conf = field_conf
    @temp_field_name = "_#{field_conf.name}"
  end

  def generate
    if @field_conf.required?
      generate_required_bson_value_code
    else
      generate_optional_bson_value_code
    end
  end

  def generate_required_bson_value_code
    "        _bsonValue.append(#{@field_conf.store_name_const_name}, #{@field_conf.getter_name}().toBsonValue());\n"
  end

  def generate_optional_bson_value_code
    code = ''
    code << "        var #{@temp_field_name} = #{@field_conf.getter_name}();\n"
    code << "        if (#{@temp_field_name} != null) {\n"
    code << generate_optional_variable_bson_value_code
    code << "        }\n"
  end

  def generate_optional_variable_bson_value_code
    "            _bsonValue.append(#{@field_conf.store_name_const_name}, #{@temp_field_name}.toBsonValue());\n"
  end

end


require_relative 'to_bson_value/to_bson_int_value_generator'
require_relative 'to_bson_value/to_bson_long_value_generator'
require_relative 'to_bson_value/to_bson_double_value_generator'
require_relative 'to_bson_value/to_bson_decimal_value_generator'
require_relative 'to_bson_value/to_bson_boolean_value_generator'
require_relative 'to_bson_value/to_bson_string_value_generator'
require_relative 'to_bson_value/to_bson_date_value_generator'
require_relative 'to_bson_value/to_bson_time_value_generator'
require_relative 'to_bson_value/to_bson_date_time_value_generator'
require_relative 'to_bson_value/to_bson_object_id_value_generator'
require_relative 'to_bson_value/to_bson_uuid_value_generator'
require_relative 'to_bson_value/to_bson_std_list_value_generator'
