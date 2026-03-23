require_relative '../to_bson_field_value_generator'


class ToBsonStringValueGenerator < ToBsonFieldValueGenerator

  def generate_required_bson_value_code
    "        _bsonValue.put(#{field_conf.store_name_const_name}, new BsonString(#{field_conf.getter_name}()));\n"
  end

  def generate_optional_variable_bson_value_code
    "            _bsonValue.put(#{field_conf.store_name_const_name}, new BsonString(#{temp_field_name}));\n"
  end

end
