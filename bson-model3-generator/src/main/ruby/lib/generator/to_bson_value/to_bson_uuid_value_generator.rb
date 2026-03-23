require_relative '../to_bson_field_value_generator'


class ToBsonUuidValueGenerator < ToBsonFieldValueGenerator

  def generate_required_bson_value_code
    "        _bsonValue.put(#{field_conf.store_name_const_name}, #{to_bson_value_code("#{field_conf.getter_name}()")});\n"
  end

  def generate_optional_variable_bson_value_code
    "            _bsonValue.put(#{field_conf.store_name_const_name}, #{to_bson_value_code(temp_field_name)});\n"
  end

  private
  def to_bson_value_code(variable_name)
    if field_conf.has_modifier?('legacy')
      "new BsonBinary(#{variable_name}, UuidRepresentation.JAVA_LEGACY)"
    else
      "new BsonBinary(#{variable_name})"
    end
  end

end
