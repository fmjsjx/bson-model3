require_relative '../to_store_field_data_generator'


class ToStoreStdListDataGenerator < ToStoreFieldDataGenerator

  def generate_required_to_store_data_code
    "        _storeData.#{field_conf.name} = #{field_conf.getter_name}()#{value_map_code};\n"
  end

  def generate_optional_variable_to_store_data_code
    "            _storeData.#{field_conf.name} = #{temp_field_name}#{value_map_code};\n"
  end

  private
  def value_map_code
    case field_conf.value
    when 'date'
      ".stream().map(DateTimeUtil::toNumber).toList()"
    when 'time'
      ".stream().map(DateTimeUtil::toNumber).toList()"
    when 'datetime'
      ".stream().map(DateTimeUtil::toEpochMilli).toList()"
    when 'object-id'
      ".stream().map(ObjectId::toHexString).toList()"
    when 'uuid'
      ".stream().map(UUID::toString).toList()"
    else
      ''
    end
  end

end
