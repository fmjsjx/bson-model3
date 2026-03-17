require_relative '../load_field_generator'
require_relative '../default_value/date_time_default_value'


class LoadDateTimeGenerator < LoadFieldGenerator

  def generate_load_required_field_code
    if has_default_value?
      "        #{@field_conf.name} = BsonUtil.dateTimeValue(src, #{@field_conf.store_name_const_name}).orElse(#{default_value_code});\n"
    else
      "        #{@field_conf.name} = BsonUtil.dateTimeValue(src, #{@field_conf.store_name_const_name}).orElse(BsonModelConstants.EPOCH_DATE_TIME);\n"
    end
  end

  def generate_load_optional_field_code
    "        #{@field_conf.name} = BsonUtil.dateTimeValue(src, #{@field_conf.store_name_const_name}).orElse(null);\n"
  end

  private

  def has_default_value?
    not @field_conf.default.nil?
  end

  def default_value_code
    DateTimeDefaultValue.instance.generate_code(@config, @model_conf, @field_conf)
  end

end
