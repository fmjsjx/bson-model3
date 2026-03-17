class LoadStoreFieldDataGenerator

  class << self
  
    def from(config, model_conf, field_conf)
      case field_conf.type
      when 'int', 'long', 'double', 'decimal', 'boolean', 'string'
        LoadStoreSimpleDataGenerator.new(config, model_conf, field_conf)
      when 'date'
        LoadStoreDateDataGenerator.new(config, model_conf, field_conf)
      when 'time'
        LoadStoreTimeDataGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        LoadStoreDateTimeDataGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        LoadStoreObjectIdDataGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        LoadStoreUuidDataGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        LoadStoreStdListDataGenerator.new(config, model_conf, field_conf)
      when 'map'
        LoadStoreMapDataGenerator.new(config, model_conf, field_conf)
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
    if field_conf.required?
      generate_required_load_store_data_code
    else
      generate_optional_load_store_data_code
    end
  end

  def generate_required_load_store_data_code
    "            #{@field_conf.getter_name}().loadStoreData(_storeData.#{@field_conf.name});\n"
  end

  def generate_optional_load_store_data_code
    code = ''
    code << "            var #{@temp_field_name} = _storeData.#{@field_conf.name};\n"
    code << "            if (#{@temp_field_name} != null) {\n"
    code << generate_optional_variable_load_store_data_code
    code << "            }\n"
  end

  def generate_optional_variable_load_store_data_code
    "                #{@field_conf.name} = new #{@field_conf.model}().parent(this).index(#{@field_conf.field_index_const_name}).key(#{@field_conf.store_name_const_name}).loadStoreData(#{temp_field_name});\n"
  end

end


require_relative 'load_store_data/load_store_simple_data_generator'
require_relative 'load_store_data/load_store_date_data_generator'
require_relative 'load_store_data/load_store_time_data_generator'
require_relative 'load_store_data/load_store_date_time_data_generator'
require_relative 'load_store_data/load_store_object_id_data_generator'
require_relative 'load_store_data/load_store_uuid_data_generator'
require_relative 'load_store_data/load_store_std_list_data_generator'
require_relative 'load_store_data/load_store_map_data_generator'
