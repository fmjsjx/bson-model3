require_relative '../load_store_field_data_generator'


class LoadStoreSimpleDataGenerator < LoadStoreFieldDataGenerator

  def generate_required_load_store_data_code
    "            #{field_conf.name} = _storeData.#{field_conf.name};\n"
  end

  def generate_optional_variable_load_store_data_code
    "                #{field_conf.name} = #{temp_field_name};\n"
  end

end
