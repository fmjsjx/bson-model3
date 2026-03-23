require_relative '../load_store_field_data_generator'


class LoadStoreStdListDataGenerator < LoadStoreFieldDataGenerator

  def generate_required_load_store_data_code
    "            #{field_conf.name} = _storeData.#{field_conf.name}#{value_map_code};\n"
  end

  def generate_optional_variable_load_store_data_code
    "                #{field_conf.name} = #{temp_field_name}#{value_map_code};\n"
  end

  private
  def value_map_code
    case field_conf.value
    when 'date'
      '.stream().map(DateTimeUtil::toDate).toList()'
    when 'time'
      '.stream().map(DateTimeUtil::toTime).toList()'
    when 'datetime'
      '.stream().map(DateTimeUtil::ofEpochMilli).toList()'
    when 'object-id'
      '.stream().map(ObjectId::new).toList()'
    when 'uuid'
      '.stream().map(UUID::fromString).toList()'
    else
      ''
    end
  end

end
