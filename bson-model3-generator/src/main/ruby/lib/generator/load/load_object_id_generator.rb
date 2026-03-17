require_relative '../load_field_generator'


class LoadObjectIdGenerator < LoadFieldGenerator

  def generate_load_required_field_code
    "        #{@field_conf.name} = BsonUtil.objectIdValue(src, #{@field_conf.store_name_const_name}).orElseThrow();\n"
  end

  def generate_load_optional_field_code
    "        #{@field_conf.name} = BsonUtil.objectIdValue(src, #{@field_conf.store_name_const_name}).orElse(null);\n"
  end

end
