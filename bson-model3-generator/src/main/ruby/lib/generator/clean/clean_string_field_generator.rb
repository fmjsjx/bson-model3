require_relative '../clean_field_generator'
require_relative '../default_value/string_default_value'


class CleanStringFieldGenerator < CleanFieldGenerator

  def generate_required_clean_code
    if has_default_value?
      "        #{@field_conf.name} = #{default_value_code};\n"
    else
      "        #{@field_conf.name} = \"\";\n"
    end
  end

  def generate_optional_clean_code
    "        #{@field_conf.name} = null;\n"
  end

  private
  def default_value_code
    StringDefaultValue.generate_class_code(@config, @model_conf, @field_conf)
  end

end

