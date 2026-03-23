require_relative '../to_bson_field_value_generator'


class ToBsonStdListValueGenerator < ToBsonFieldValueGenerator

  def generate_required_bson_value_code
    "        _bsonValue.put(#{field_conf.store_name_const_name}, BsonValueUtil.toBsonArray(#{field_conf.getter_name}(), #{map_to_bson_value_code}));\n"
  end

  def generate_optional_variable_bson_value_code
    "            _bsonValue.put(#{field_conf.store_name_const_name}, BsonValueUtil.toBsonArray(#{temp_field_name}, #{map_to_bson_value_code}));\n"
  end

  private

  def map_to_bson_value_code
    value_type = field_conf.value
    case value_type
    when 'int'
      "BsonInt32::new"
    when 'long'
      "BsonInt64::new"
    when 'double'
      "BsonDouble::new"
    when 'decimal'
      "BsonValueUtil::toBsonDecimal128"
    when 'string'
      "BsonString::new"
    when 'date'
      'BsonValueUtil::toBsonInt32'
    when 'time'
      'BsonValueUtil::toBsonInt32'
    when 'datetime'
      'BsonValueUtil::toBsonDateTime'
    when 'object-id'
      'BsonObjectId::new'
    when 'uuid'
      if field_conf.has_modifier?('legacy')
        'it -> new BsonBinary(it, UuidRepresentation.JAVA_LEGACY)'
      else
        'BsonBinary::new'
      end
    else
      raise ArgumentError, "Unsupported value type: #{value_type}"
    end
  end

end
