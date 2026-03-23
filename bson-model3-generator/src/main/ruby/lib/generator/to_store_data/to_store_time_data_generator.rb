require_relative '../to_store_field_data_generator'


class ToStoreTimeDataGenerator < ToStoreFieldDataGenerator

  def generate_required_to_store_data_code
    "        _storeData.#{field_conf.name} = DateTimeUtil.toNumber(#{field_conf.getter_name}());\n"
  end

  def generate_optional_variable_to_store_data_code
    "            _storeData.#{field_conf.name} = DateTimeUtil.toNumber(#{temp_field_name});\n"
  end

end
