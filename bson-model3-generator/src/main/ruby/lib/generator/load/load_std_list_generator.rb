require_relative '../load_field_generator'


class LoadStdListGenerator < LoadFieldGenerator

  def generate_load_required_field_code
    "        #{@field_conf.name} = BsonUtil.arrayValue(src, #{@field_conf.store_name_const_name}).map(#{map_code}).orElse(List.of());\n"
  end

  def generate_load_optional_field_code
    "        #{@field_conf.name} = BsonUtil.arrayValue(src, #{@field_conf.store_name_const_name}).map(#{map_code}).orElse(null);\n"
  end

  private
  def map_code
    value_type = field_conf.value
    case value_type
    when 'int'
      'BsonValueUtil::mapToIntegerList'
    when 'long'
      'BsonValueUtil::mapToLongList'
    when 'double'
      'BsonValueUtil::mapToDoubleList'
    when 'decimal'
      'BsonValueUtil::mapToBigDecimalList'
    when 'string'
      'BsonValueUtil::mapToStringList'
    when 'date'
      'BsonValueUtil::mapToLocalDateList'
    when 'time'
      'BsonValueUtil::mapToLocalTimeList'
    when 'datetime'
      'BsonValueUtil::mapToLocalDateTimeList'
    when 'object-id'
      'BsonValueUtil::mapToObjectIdList'
    when 'uuid'
      if field_conf.has_modifier?('legacy')
        'it -> BsonValueUtil.mapToUuidList(it, UuidRepresentation.JAVA_LEGACY)'
      else
        'BsonValueUtil::mapToUuidList'
      end
    when 'object'
      "it -> BsonValueUtil.mapToObjectList(it, (bson) -> new #{field_conf.model}().load(bson))"
    else
      raise ArgumentError, "Unsupported value type: #{value_type}"
    end
  end

end
