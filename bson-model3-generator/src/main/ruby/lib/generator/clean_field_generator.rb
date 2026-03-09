class CleanFieldGenerator

  class << self

    def from(config, model_conf, field_conf)
      case field_conf.type
      when 'int'
        CleanIntFieldGenerator.new(config, model_conf, field_conf)
      when 'long'
        CleanLongFieldGenerator.new(config, model_conf, field_conf)
      when 'double'
        CleanDoubleFieldGenerator.new(config, model_conf, field_conf)
      when 'boolean'
        CleanBooleanFieldGenerator.new(config, model_conf, field_conf)
      when 'string'
        CleanStringFieldGenerator.new(config, model_conf, field_conf)
      when 'date'
        CleanDateFieldGenerator.new(config, model_conf, field_conf)
      when 'time'
        CleanTimeFieldGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        CleanDateTimeFieldGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        CleanObjectIdFieldGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        CleanUuidFieldGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        CleanStdListFieldGenerator.new(config, model_conf, field_conf)
      else
        new(config, model_conf, field_conf)
      end
    end

  end

  attr_reader :config, :model_conf, :field_conf

  def initialize(config, model_conf, field_conf)
    @config = config
    @model_conf = model_conf
    @field_conf = field_conf
  end

  def required?
    @field_conf.required?
  end

  def store_field?
    @field_conf.store_field?
  end

  def has_default_value?
    not @field_conf.default.nil?
  end

  def generate
    if store_field?
      if required?
        generate_required_clean_code
      else
        generate_optional_clean_code
      end
    else
      "        #{@field_conf.name} = null;\n"
    end
  end

  def generate_required_clean_code
    "        #{@field_conf.name}.clean();\n"
  end

  def generate_optional_clean_code
    code = ''
    code << "        var _#{@field_conf.name} = #{@field_conf.name};\n"
    code << "        if (_#{@field_conf.name} != null) {\n"
    code << "            _#{@field_conf.name}.clean().detach();\n"
    code << "            #{@field_conf.name} = null;\n"
    code << "        }\n"
  end

end


require_relative 'clean/clean_int_field_generator'
require_relative 'clean/clean_long_field_generator'
require_relative 'clean/clean_double_field_generator'
require_relative 'clean/clean_boolean_field_generator'
require_relative 'clean/clean_string_field_generator'
require_relative 'clean/clean_date_field_generator'
require_relative 'clean/clean_time_field_generator'
require_relative 'clean/clean_date_time_field_generator'
require_relative 'clean/clean_object_id_field_generator'
require_relative 'clean/clean_uuid_field_generator'
require_relative 'clean/clean_std_list_field_generator'
