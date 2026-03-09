require_relative '../clean_field_generator'


class CleanObjectIdFieldGenerator < CleanFieldGenerator

  def generate_required_clean_code
    code = ''
    code << "        //noinspection DataFlowIssue\n"
    code << "        #{@field_conf.name} = null;\n"
  end

  def generate_optional_clean_code
    "        #{@field_conf.name} = null;\n"
  end

end
