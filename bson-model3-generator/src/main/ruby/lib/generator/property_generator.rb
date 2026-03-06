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

  def virtual?
    @field_conf.virtual?
  end

  def no_setter?
    virtual?
  end

  def virtual_code
    if virtual?
      if not @field_conf.block.nil?
        @field_conf.block.lines.map { |line| "        #{line.chomp}" }.join("\n")
      elsif not @field_conf.expression.nil?
        "        return #{@field_conf.expression};"
      else
        raise ArgumentError, "At least one of block or expression must be present"
      end
    end
  end

  def field_changed_code
    associated_fields = @model_conf.fields.filter do |field|
      field.name == @field_conf.name or (field.virtual? and field.sources.include?(@field_conf.name))
    end.map do |field|
      field.field_index_const_name
    end
    if associated_fields.size == 1
      "triggerChange(#{associated_fields[0]});"
    else
      "fieldsChanged(#{associated_fields.join(', ')});"
    end
  end

  def generate_field_declaration_code
    raise UnsupportedOperationException, "generate_field_declaration_code is not supported on #{self.class}"
  end

  def generate_getter_code
    raise UnsupportedOperationException, "generate_getter_code is not supported on #{self.class}"
  end

  def generate_setter_code
    raise UnsupportedOperationException, "generate_setter_code is not supported on #{self.class}"
  end

  def generate_increment_code
    raise UnsupportedOperationException, "generate_increment_code is not supported on #{self.class}"
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
