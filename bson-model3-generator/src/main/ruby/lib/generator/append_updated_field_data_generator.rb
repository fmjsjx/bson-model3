class AppendUpdatedFieldDataGenerator

  class << self

    def from(field_conf, config, model_conf)
      case field_conf.type
      when 'int', 'long', 'double', 'decimal', 'boolean', 'string'
        AppendUpdatedSimpleDataGenerator.new(config, model_conf, field_conf)
      when 'date'
        AppendUpdatedDateDataGenerator.new(config, model_conf, field_conf)
      when 'time'
        AppendUpdatedTimeDataGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        AppendUpdatedDateTimeDataGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        AppendUpdatedObjectIdDataGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        AppendUpdatedUuidDataGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        AppendUpdatedStdListDataGenerator.new(config, model_conf, field_conf)
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
    code = ''
    code << "        if (changedFields.get(#{@field_conf.field_index_const_name})) {\n"
    code << generate_append_code
    code << "        }\n"
  end

  def generate_append_code
    code = ''
    if field_conf.required?
      code << "            var #{@temp_field_name} = #{@field_conf.getter_name}().toUpdated();\n"
      code << "            if (#{@temp_field_name} != null) {\n"
      code << "                data.put(#{@field_conf.display_name_const_name}, #{@temp_field_name});\n"
      code << "            }\n"
    else
      code << "            var #{@temp_field_name} = #{@field_conf.getter_name}();\n"
      code << "            if (#{@temp_field_name} != null) {\n"
      code << "                var #{@temp_field_name}Updated = #{@temp_field_name}.toUpdated();\n"
      code << "                if (#{@temp_field_name}Updated != null) {\n"
      code << "                    data.put(#{@field_conf.display_name_const_name}, #{@temp_field_name}Updated);\n"
      code << "                }\n"
      code << "            }\n"
    end
  end

end


require_relative 'append_updated_data/append_updated_simple_data_generator'
require_relative 'append_updated_data/append_updated_date_data_generator'
require_relative 'append_updated_data/append_updated_time_data_generator'
require_relative 'append_updated_data/append_updated_date_time_data_generator'
require_relative 'append_updated_data/append_updated_object_id_data_generator'
require_relative 'append_updated_data/append_updated_uuid_data_generator'
require_relative 'append_updated_data/append_updated_std_list_data_generator'
