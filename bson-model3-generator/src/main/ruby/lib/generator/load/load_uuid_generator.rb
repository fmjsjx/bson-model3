require_relative '../load_field_generator'


class LoadUuidGenerator < LoadFieldGenerator

  def generate_load_required_field_code
    if field_conf.has_modifier?('legacy')
      "        #{@field_conf.name} = BsonUtil.uuidValue(src, #{@field_conf.store_name_const_name}, UuidRepresentation.JAVA_LEGACY).orElseThrow();\n"
    else
      "        #{@field_conf.name} = BsonUtil.uuidValue(src, #{@field_conf.store_name_const_name}).orElseThrow();\n"
    end
  end

  def generate_load_optional_field_code
    if field_conf.has_modifier?('legacy')
      "        #{@field_conf.name} = BsonUtil.uuidValue(src, #{@field_conf.store_name_const_name}, UuidRepresentation.JAVA_LEGACY).orElse(null);\n"
    else
      "        #{@field_conf.name} = BsonUtil.uuidValue(src, #{@field_conf.store_name_const_name}).orElse(null);\n"
    end
  end

end
