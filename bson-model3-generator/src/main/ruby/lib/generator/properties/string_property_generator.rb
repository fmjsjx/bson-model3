require_relative '../property_generator'
require 'json'


class StringPropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private String #{name} = #{default_value_code};\n"
      else
        "    private String #{name} = \"\";\n"
      end
    else
      "    private @Nullable String #{name};\n"
    end
  end

  private
  def default_value_code
    field_conf.default.to_json
  end

end
