require_relative '../to_store_field_data_generator'


class ToStoreMapDataGenerator < ToStoreFieldDataGenerator

  def generate_required_to_store_data_code
    "        _storeData.#{field_conf.name} = (Map<String, #{store_data_value_type}>) #{field_conf.getter_name}().toStoreData();\n"
  end

  def generate_optional_variable_to_store_data_code
    "            _storeData.#{field_conf.name} = (Map<String, #{store_data_value_type}>) #{temp_field_name}.toStoreData();\n"
  end

  private
  def store_data_value_type
    case field_conf.value
    when 'int'
      'Integer'
    when 'long', 'datetime'
      'Long'
    when 'double'
      'Double'
    when 'decimal'
      'BigDecimal'
    when 'string'
      'String'
    when 'object'
      "#{@field_conf.model}.#{@field_conf.model}StoreData"
    else
      raise ArgumentError, "Unsupported value type: #{value}"
    end
  end

end
