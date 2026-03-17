require_relative '../to_store_field_data_generator'


class ToStoreObjectIdDataGenerator < ToStoreFieldDataGenerator

  def generate_required_to_store_data_code
    "        _storeData.#{field_conf.name} = #{field_conf.getter_name}().toHexString();\n"
  end

  def generate_optional_variable_to_store_data_code
    "            _storeData.#{field_conf.name} = #{temp_field_name}.toHexString();\n"
  end

end
