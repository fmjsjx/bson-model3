class PropertyGenerator

  class << self
    def from(config, model_conf, field_conf)
      type = field_conf.type
      case type
      when 'int'
        IntPropertyGenerator.new(config, model_conf, field_conf)
      when 'long'
        LongPropertyGenerator.new(config, model_conf, field_conf)
      when 'double'
        DoublePropertyGenerator.new(config, model_conf, field_conf)
      when 'boolean'
        BooleanPropertyGenerator.new(config, model_conf, field_conf)
      when 'string'
        StringPropertyGenerator.new(config, model_conf, field_conf)
      when 'date'
        DatePropertyGenerator.new(config, model_conf, field_conf)
      when 'time'
        TimePropertyGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        DateTimePropertyGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        ObjectIdPropertyGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        UuidPropertyGenerator.new(config, model_conf, field_conf)
      when 'object'
        ObjectPropertyGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        StdListPropertyGenerator.new(config, model_conf, field_conf)
      when 'map'
        MapPropertyGenerator.new(config, model_conf, field_conf)
      else
        raise ArgumentError, "unsupported field type #{type}"
      end
    end
  end

  attr_reader :config, :model_conf, :field_conf

  def initialize(config, model_conf, field_conf)
    @config = config
    @model_conf = model_conf
    @field_conf = field_conf
  end

  def name
    @field_conf.name
  end

  def required?
    @field_conf.required?
  end

  def has_default_value?
    not @field_conf.default.nil?
  end

  def store_field?
    @field_conf.store_field?
  end

  def generate_field_declaration_code
    raise NotImplementedError, "generate_field_declaration_code is not implemented"
  end

end

require_relative 'properties/int_property_generator'
require_relative 'properties/long_property_generator'
require_relative 'properties/double_property_generator'
require_relative 'properties/boolean_property_generator'
require_relative 'properties/string_property_generator'
require_relative 'properties/date_property_generator'
require_relative 'properties/time_property_generator'
require_relative 'properties/date_time_property_generator'
require_relative 'properties/object_id_property_generator'
require_relative 'properties/uuid_property_generator'
require_relative 'properties/object_property_generator'
require_relative 'properties/std_list_property_generator'
require_relative 'properties/map_property_generator'
