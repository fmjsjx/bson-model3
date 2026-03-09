require_relative '../clean_field_generator'


class CleanStdListFieldGenerator < CleanFieldGenerator

  def generate_required_clean_code
    "        #{@field_conf.name} = List.of();\n"
  end

  def generate_optional_clean_code
    "        #{@field_conf.name} = null;\n"
  end

end
