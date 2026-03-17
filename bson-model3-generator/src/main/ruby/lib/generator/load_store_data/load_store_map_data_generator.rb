require_relative '../load_store_field_data_generator'
require_relative '../properties/map_property_generator'


class LoadStoreMapDataGenerator < LoadStoreFieldDataGenerator

  attr_reader :property_generator

  def initialize(config, model_conf, field_conf)
    super(config, model_conf, field_conf)
    @property_generator = MapPropertyGenerator.new(config, model_conf, field_conf)
  end

  def generate_optional_variable_load_store_data_code
    code = ''
    code << "                #{field_conf.name} = #{@property_generator.generate_init_code}\n"
    code << "                        .parent(this).index(#{field_conf.field_index_const_name}).key(#{field_conf.store_name_const_name})\n"
    code << "                        .loadStoreData(#{temp_field_name});\n"
  end

end
