class ToDisplayFieldDataGenerator

  class << self

    def from(field_conf, config, model_conf)
      case field_conf.type
      when 'int', 'long', 'double', 'decimal', 'boolean', 'string'
        ToDisplaySimpleDataGenerator.new(config, model_conf, field_conf)
      when 'date'
        ToDisplayDateDataGenerator.new(config, model_conf, field_conf)
      when 'time'
        ToDisplayTimeDataGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        ToDisplayDateTimeDataGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        ToDisplayObjectIdDataGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        ToDisplayUuidDataGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        ToDisplayStdListDataGenerator.new(config, model_conf, field_conf)
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
    if @field_conf.transient? or @field_conf.readonly? or not @field_conf.required?
      generate_optional_display_data_code
    else
      generate_required_display_data_code
    end
  end

  def generate_required_display_data_code
    "        _displayData.put(#{@field_conf.display_name_const_name}, #{@field_conf.getter_name}().toDisplayData());\n"
  end

  def generate_optional_display_data_code
    code = ''
    code << "        var #{@temp_field_name} = #{@field_conf.getter_name}();\n"
    code << "        if (#{@temp_field_name} != null) {\n"
    code << generate_optional_variable_display_data_code
    code << "        }\n"
  end

  def generate_optional_variable_display_data_code
    "            _displayData.put(#{@field_conf.display_name_const_name}, #{@temp_field_name}.toDisplayData());\n"
  end

end


require_relative 'to_display_data/to_display_simple_data_generator'
require_relative 'to_display_data/to_display_date_data_generator'
require_relative 'to_display_data/to_display_time_data_generator'
require_relative 'to_display_data/to_display_date_time_data_generator'
require_relative 'to_display_data/to_display_object_id_data_generator'
require_relative 'to_display_data/to_display_uuid_data_generator'
require_relative 'to_display_data/to_display_std_list_data_generator'
