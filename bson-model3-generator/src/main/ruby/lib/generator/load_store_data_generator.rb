require_relative 'load_store_field_data_generator'


class LoadStoreDataGenerator

  attr_reader :config, :model_conf, :field_confs, :load_store_field_data_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.filter { |field_conf| field_conf.store_field? }
    @load_store_field_data_generators = @field_confs.map do |field_conf|
      LoadStoreFieldDataGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public #{@model_conf.name} loadStoreData(Object data) {\n"
    code << "        resetStates();\n"
    code << "        if (data instanceof #{@model_conf.name}StoreData _storeData) {\n"
    @load_store_field_data_generators.each do |load_store_field_data_generator|
      code << load_store_field_data_generator.generate
    end
    code << "        }\n"
    code << "        return this;\n"
    code << "    }\n"
  end

end
