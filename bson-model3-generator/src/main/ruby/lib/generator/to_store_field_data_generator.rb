class ToStoreFieldDataGenerator

  class << self

    def from(config, model_conf, field_conf)
      case field_conf.type
      when 'int', 'long', 'double', 'decimal', 'boolean', 'string'
        ToStoreSimpleDataGenerator.new(config, model_conf, field_conf)
      when 'date'
        ToStoreDateDataGenerator.new(config, model_conf, field_conf)
      when 'time'
        ToStoreTimeDataGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        ToStoreDateTimeDataGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        ToStoreObjectIdDataGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        ToStoreUuidDataGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        ToStoreStdListDataGenerator.new(config, model_conf, field_conf)
      when 'map'
        ToStoreMapDataGenerator.new(config, model_conf, field_conf)
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
      generate_required_to_store_data_code
    else
      generate_optional_to_store_data_code
    end
  end

  def generate_required_to_store_data_code
    "        _storeData.#{@field_conf.name} = #{@field_conf.getter_name}().toStoreData();\n"
  end

  def generate_optional_to_store_data_code
    code = ''
    code << "        var #{@temp_field_name} = #{@field_conf.getter_name}();\n"
    code << "        if (#{@temp_field_name} != null) {\n"
    code << generate_optional_variable_to_store_data_code
    code << "        }\n"
  end

  def generate_optional_variable_to_store_data_code
    "            _storeData.#{@field_conf.name} = #{@temp_field_name}.toStoreData();\n"
  end

end


require_relative 'to_store_data/to_store_simple_data_generator'
require_relative 'to_store_data/to_store_date_data_generator'
require_relative 'to_store_data/to_store_time_data_generator'
require_relative 'to_store_data/to_store_date_time_data_generator'
require_relative 'to_store_data/to_store_object_id_data_generator'
require_relative 'to_store_data/to_store_uuid_data_generator'
require_relative 'to_store_data/to_store_std_list_data_generator'
require_relative 'to_store_data/to_store_map_data_generator'
