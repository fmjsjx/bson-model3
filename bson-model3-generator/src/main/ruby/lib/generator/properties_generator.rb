require_relative 'property_generator'


class PropertiesGenerator

  attr_reader :config, :model_conf, :property_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @property_generators = model_conf.fields.map do |field_conf|
      PropertyGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate_fields_code
    code = "\n"
    @property_generators.each do |property_generator|
      unless property_generator.field_conf.virtual?
        code << property_generator.generate_field_declaration_code
      end
    end
    code
  end

end
