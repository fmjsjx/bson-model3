require_relative '../load_field_generator'
require_relative '../default_value/double_default_value'


class LoadDoubleGenerator < LoadFieldGenerator

  def generate_load_required_field_code
    if has_default_value?
      "        #{@field_conf.name} = BsonUtil.doubleValue(src, #{@field_conf.store_name_const_name}).orElse(#{default_value_code});\n"
    else
      "        #{@field_conf.name} = BsonUtil.doubleValue(src, #{@field_conf.store_name_const_name}).orElse(Double.NaN);\n"
    end
  end

  def generate_load_optional_field_code
    "        #{@field_conf.name} = BsonUtil.doubleValue(src, #{@field_conf.store_name_const_name}).orElse(null);\n"
  end

  private

  def has_default_value?
    not @field_conf.default.nil?
  end

  def default_value_code
    DoubleDefaultValue.instance.generate_code(@config, @model_conf, @field_conf)
  end

end
