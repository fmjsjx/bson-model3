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

  def generate_properties_code
    code = generate_fields_code
    code << generate_getters_setters_code
  end

  private
  def generate_fields_code
    code = "\n"
    @property_generators.each do |property_generator|
      unless property_generator.field_conf.virtual?
        code << property_generator.generate_field_declaration_code
      end
    end
    code
  end

  def generate_getters_setters_code
    code = ''
    @property_generators.each do |property_generator|
      # generate getter
      code << "\n"
      property_generator.field_conf.annotations.each do |annotation|
        code << "    #{annotation}\n"
      end
      code << property_generator.generate_getter_code
      unless property_generator.no_setter?
        # generate setter
        code << "\n"
        code << property_generator.generate_setter_code
      end
      if property_generator.field_conf.increment?
        code << "\n"
        code << property_generator.generate_increment_code
      end
    end
    code
  end

end
