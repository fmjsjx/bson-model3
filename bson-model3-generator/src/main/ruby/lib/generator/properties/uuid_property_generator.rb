require_relative '../property_generator'


class UuidPropertyGenerator < PropertyGenerator

  def initialize(config, model_conf, field_conf)
    super(config, model_conf, field_conf)
  end

  def generate_field_declaration_code
    if required?
      "    private UUID #{name};\n"
    else
      "    private @Nullable UUID #{name};\n"
    end
  end

end
